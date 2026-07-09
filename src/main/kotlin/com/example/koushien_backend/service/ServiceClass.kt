package com.example.koushien_backend.service
import aws.sdk.kotlin.services.bedrockruntime.BedrockRuntimeClient
import aws.sdk.kotlin.services.bedrockruntime.model.ContentBlock
import aws.sdk.kotlin.services.bedrockruntime.model.ConversationRole
import aws.sdk.kotlin.services.bedrockruntime.model.ConverseRequest
import aws.sdk.kotlin.services.bedrockruntime.model.ImageBlock
import aws.sdk.kotlin.services.bedrockruntime.model.Message
import org.springframework.stereotype.Service
import java.util.Base64
import aws.sdk.kotlin.services.bedrockruntime.model.ImageFormat
import aws.sdk.kotlin.services.bedrockruntime.model.ImageSource

@Service
open class ServiceClass() : Services {
    override suspend fun chat(ques: String, img: String): String {
        BedrockRuntimeClient { region = "us-east-1" }.use { client ->

            val modelId = "amazon.nova-lite-v1:0"


            // Create the message with the user's prompt
            val prompt = """あなたは、企業研修における講師支援AIです。  あなたの役割は、受講者の「わからない」反応が一定数以上集まったタイミングで、 その直前の講義文脈を読み取り、講師が補足すべき可能性のある内容を短く示すことです。  入力として、以下が与えられます。  1. 現在表示されているスライド情報 2. 「わからない」反応が発生した時点より前の文字起こしデータ  あなたは、スライド内容と直前の発話内容を照合し、 受講者が「どの説明・用語・概念・話のつながり」でつまずいた可能性が高いかを推定してください。  ただし、受講者の内心を断定してはいけません。 「受講者は理解していない」と断定するのではなく、 「〇〇が理解しにくい恐れがあります」 「〇〇の関係が分かりにくい可能性があります」 「〇〇を補足するとよさそうです」 のように、講師が次に補足しやすい示唆文として出力してください。  出力するcontextは、講師画面にそのまま表示される短いメッセージです。 必ず70文字以内にしてください。  以下の優先順位で、補足すべき内容を特定してください。  1. 直前1分以内に新しく出てきた専門用語・略語・固有表現 2. 説明なし、または短い説明で流された重要語句 3. スライド上の重要語句と、発話で補足された説明のずれ 4. 前に話した内容と、今話している内容の関係・矛盾・使い分け 5. 数字、条件、手順、判断基準など、理解負荷が高い説明 6. 発話の中で説明が短く、受講者が置いていかれやすい箇所  専門用語を含む長い表現がある場合は、 文全体ではなく、受講者が最もつまずきやすい最小単位の用語を優先してください。  例： 「オプトアウトが設定された社内環境での利用」 → 「オプトアウトという用語が理解しにくい恐れがあります」  例： 「AIは間違えるが、まず任せてみる」 → 「AIに任せる範囲と人が確認する範囲が曖昧な恐れがあります」  該当箇所が複数ある場合は、 「講師が今すぐ補足すると最も効果が高そうな内容」を1つだけ選んでください。  十分に推定できない場合は、無理に断定せず、 「直前説明の要点を補足するとよさそうです」 または 「直前の用語や条件を整理するとよさそうです」 と出力してください。  出力形式は以下に従ってください。  {   "context": "70文字以内の講師向け補足示唆文",   "confidence": "high / medium / low",   "reason": "なぜその文脈だと判断したかを80文字以内で説明" }  制約： - contextは必ず70文字以内 - contextは講師画面にそのまま表示できる自然な日本語にする - contextは「〇〇が理解しにくい恐れがあります」「〇〇を補足するとよさそうです」の形を優先する - 「受講者は理解していません」「講師の説明が悪い」などの断定・責める表現は禁止 - 入力にない情報を推測で追加しない - reasonは検証用であり、講師画面に必ず表示する必要はない"""
            //↑本番用プロンプト

            val promptText = ContentBlock.Text(prompt)

            val image:ByteArray = Base64.getMimeDecoder().decode(img)

            val imageBlock = ContentBlock.Image(ImageBlock {
                source = ImageSource.Bytes(image)
                format = ImageFormat.Jpeg
            })

            val context = ContentBlock.Text(ques)

            val message = Message {
                role = ConversationRole.User
                content = listOf(imageBlock,promptText,context)
            }

            // Configure the request with optional model parameters
            val request = ConverseRequest {
                this.modelId = modelId
                messages = listOf(message)
                inferenceConfig {
                    maxTokens = 500 // Maximum response length
                    temperature = 0.5F // Lower values: more focused output
                    // topP = 0.8F // Alternative to temperature
                }
            }

            // Send the request and process the model's response
            runCatching {
                val response = client.converse(request)
                return response.output!!.asMessage().content.first().asText()
            }.getOrElse { error ->
                error.message?.let { e -> System.err.println("ERROR: Can't invoke '$modelId'. Reason: $e") }
                throw RuntimeException("Failed to generate text with model $modelId", error)
            }
        }
    }
}