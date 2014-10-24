package org.groovy.collectionjson

import org.groovy.collectionjson.*
import groovy.json.JsonSlurper
import spock.lang.*

class CollectionJsonSpec extends Specification {
  CollectionJson collectionJson
  JsonSlurper jsonSlurper

  def setup() {
    collectionJson = new CollectionJson()
    jsonSlurper = new JsonSlurper()
  }

  def "create should not return null"() {

    expect:
    collectionJson.create() != null
  }

  def "valid collection object should have version and href property"() {
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.version == null
    result.collection.href == null
  }

  def "version should be customisable"() {
    collectionJson.version = "1.0"
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.version == "1.0"
  }

  def "href should be customisable"() {
    collectionJson.href = "www.test.com"
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.href == "www.test.com"
  }

  def "collection object MAY have an items array property"() {
    Items items = new Items()
    collectionJson.addItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items[0] != null
  }

  def "collectionjson can add multiple items"() {
    Items items = new Items()
    collectionJson.addItems(items).addItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items.size() == 2
  }

  def "A collection's items' data array can store objects with different value types"() {
    given: "that value property MAY contain one of the following data types: STRING, NUMBER, true, false or null"
    Data stringData = new Data(value: "string")
    Data integerData = new Data(value: 0)
    Data booleanData = new Data(value: false)
    Data nullData = new Data(value: null )
    Items item = new Items()
    item.addData(stringData).addData(integerData).addData(booleanData).addData(nullData)

    when: "collectionJson is created with the different value types"
    collectionJson.addItems(item)
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    then: "the json constructed should contain the correct value types"
    int i = 0
    result.collection.items[0].data[i].value == "string"
    result.collection.items[0].data[++i].value == 0
    result.collection.items[0].data[++i].value == false
    result.collection.items[0].data[++i].value == null
  }

  def "items array should have href property"() {
    Items items = new Items(href: "www.items.com")
    collectionJson.addItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items[0].href == "www.items.com"
  }

  def "items array may have data array child property"() {
    Items items = new Items()
    Data data = new Data()
    items.addData(data)

    expect:
    items.data[0] != null
  }

  def "items array can store multiple data objects"() {
    Items items = new Items()
    Data data = new Data()
    items.addData(data).addData(data)

    expect:
    items.data.size() == 2
  }

  def "Items array may have links array child property"() {
    Items items = new Items()
    Links link = new Links()
    items.addLink(link)

    expect:
    items.links != null
  }

  def "Items array can store multiple links"() {
    Items items = new Items()
    Links link = new Links()
    Links anotherLink = new Links()
    items.addLink(link).addLink(anotherLink)

    expect:
    items.links.size() == 2
  }

  def "Links can contain a render property"() {
    Links link = new Links(render: Render.IMAGE)

    expect:
    link.render == "image"

  }

  def "render property should be set to link if not explicitly specfied"() {
    Links link = new Links()

    expect:
    link.render == "link"
  }

  def "render property MUST be either image or link"(){
    Links link = new Links(render: Render.IMAGE)
    Links anotherLink = new Links(render: Render.LINK)

    expect:
    link.render == "image"
    anotherLink.render == "link"
  }

}
