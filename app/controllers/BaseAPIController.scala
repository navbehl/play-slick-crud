package controllers

import daos.{ BaseDAO, BaseTable, IdentifiableTable }
import models.BaseConfig
import play.api.libs.json.Format
import play.api.mvc._

import scala.concurrent.ExecutionContext
import services.BaseService
import play.api.Logger
import play.api.libs.json.{ JsError, JsSuccess, JsValue, Json }
import slick.jdbc.PostgresProfile._

import scala.concurrent.Future
import scala.util.control.NonFatal

abstract class BaseAPIController[EntityService <: BaseService[EntityDao, EntityItem, IdType], EntityDao <: BaseDAO[_ <: BaseTable[EntityItem, IdType] with IdentifiableTable[IdType], EntityItem, IdType], EntityItem <: BaseConfig[IdType], IdType: BaseColumnType](baseService: EntityService, controllerComponents: ControllerComponents)(implicit val entityFormat: Format[EntityItem], implicit val idFormat: Format[IdType], implicit val ex: ExecutionContext) extends BaseController {

  def create: Action[JsValue] = Action.async(parse.json) {
    implicit request =>
      request.body.validate[EntityItem] match {

        case requestWrapper: JsSuccess[EntityItem] =>
          baseService.create(requestWrapper.value) map {
            idOption =>
              Ok(Json.obj("id" -> idOption))
          } recover {
            case NonFatal(e) =>
              Logger.error("error is ", e)
              InternalServerError("A server error occurred")
          }

        case _: JsError => Future { BadRequest("Invalid Request") }
      }

  }

  def update: Action[JsValue] = Action.async(parse.json) {
    implicit request =>
      request.body.validate[EntityItem] match {

        case requestWrapper: JsSuccess[EntityItem] =>

          baseService.update(requestWrapper.value) map {
            _ =>
              Ok(Json.obj("success" -> "Successfully store data in table"))
          } recover {
            case NonFatal(_) =>
              InternalServerError("A server error occurred")
          }

        case e: JsError => Future {
          Logger.warn("error  " + e)
          BadRequest("Invalid Request")
        }
      }
  }

  def read(id: IdType): Action[AnyContent] = Action.async {
    implicit request =>
      baseService.find(id).map { entity =>
        Ok(Json.toJson(entity))
      }
  }

}
