package daos

import models.BaseConfig
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

/**
  Base Table
  * */
abstract class BaseTable[EntityItem <: BaseConfig[IdType], IdType: BaseColumnType](tag: Tag, name: String)
  extends Table[EntityItem](tag, name) with IdentifiableTable[IdType]

abstract class BaseDAO[TableEntity <: BaseTable[EntityItem, IdType] with IdentifiableTable[IdType], EntityItem <: BaseConfig[IdType], IdType: BaseColumnType](val dbConfigProvider: DatabaseConfigProvider)(baseQuery: TableQuery[TableEntity])
  extends HasDatabaseConfigProvider[PostgresProfile] {

  import profile.api._

  def all: Future[Seq[EntityItem]] = {
    db.run(baseQuery.sortBy(_.id.asc.nullsFirst).result)
  }

  def find(id: IdType): Future[Option[EntityItem]] = {
    db.run(baseQuery.filter(_.id === id).result.headOption)
  }

  def create(newObject: EntityItem): Future[Option[IdType]] = {
    val query = (baseQuery returning baseQuery.map(_.id)) += newObject
    db.run(query)
  }

  def createOrUpdate(entityItem: EntityItem): Future[Int] = {
    db.run(baseQuery.insertOrUpdate(entityItem))
  }

  def delete(id: IdType): Future[Int] = {
    db.run(baseQuery.filter(_.id === id).delete)
  }

  def update(updatedObject: EntityItem): Future[Int] = {
    db.run(baseQuery.filter(_.id === updatedObject.id).update(updatedObject))
  }

  def addAll(newItemList: Seq[EntityItem]): Future[Seq[Option[IdType]]] = {
    val query = baseQuery returning baseQuery.map(_.id) ++= newItemList
    db.run(query)
  }

  def updateAll(itemSeq: Seq[EntityItem]): Future[Seq[Int]] = {
    val query = DBIO.sequence(itemSeq map { itemObj =>
      baseQuery.filter(x => x.id === itemObj.id).map(aqmQu =>
        aqmQu).update(itemObj)
    })
    db.run(query)
  }

}

trait IdentifiableTable[T] {
  def id: slick.lifted.Rep[Option[T]]
}
