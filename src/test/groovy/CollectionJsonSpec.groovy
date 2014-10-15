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
    json == "{\"collection\":{\"version\":null,\"href\":null}}"
    result.collection.version == null
    result.collection.href == null
  }

}
