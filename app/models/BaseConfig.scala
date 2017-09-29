package models

import java.util.UUID

import play.api.libs.json._

import scala.language.implicitConversions

trait BaseConfig[+A] {
  val id: Option[A]
  // def copyId(idValue: Option[A]): BaseConfig[A]
}

object BaseConfig {
  implicit def convertStrToUUID(input: String): UUID = UUID.fromString(input)

  def baseSyncReads[A](implicit fmt: Format[A]): Reads[Option[A]] =
    (JsPath \ "id").readNullable[A]

  def baseSyncWrites[A](t: BaseConfig[A])(implicit fmt: Format[A]): JsObject = Json.obj(
    "id" -> t.id
  )
}
