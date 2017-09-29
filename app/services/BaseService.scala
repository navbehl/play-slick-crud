package services

import daos.{ BaseDAO, BaseTable, IdentifiableTable }
import models.BaseConfig
import slick.jdbc.PostgresProfile._

import scala.concurrent.Future

/**
 * Created by pudge on 1/24/17.
 */
class BaseService[EntityDao <: BaseDAO[_ <: BaseTable[EntityItem, IdType] with IdentifiableTable[IdType], EntityItem, IdType], EntityItem <: BaseConfig[IdType], IdType: BaseColumnType](
    baseDao: EntityDao
) {
  def all: Future[Seq[EntityItem]] = {
    baseDao.all
  }

  def find(id: IdType): Future[Option[EntityItem]] = {
    baseDao.find(id)
  }

  def create(newObject: EntityItem): Future[Option[IdType]] = {
    baseDao.create(newObject)
  }

  def delete(id: IdType): Future[Int] = {
    baseDao.delete(id)
  }

  def update(updatedObject: EntityItem): Future[Int] = {
    baseDao.update(updatedObject)
  }

  def addAll(newItemList: Seq[EntityItem]): Future[Seq[Option[IdType]]] = {
    baseDao.addAll(newItemList)
  }

  def updateAll(itemSeq: Seq[EntityItem]): Future[Seq[Int]] = {
    baseDao.updateAll(itemSeq)
  }

  def createOrUpdate(obj: EntityItem): Future[Int] = {
    baseDao.createOrUpdate(obj)
  }
}
