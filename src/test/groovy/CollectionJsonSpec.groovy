import spock.lang.*

class CollectionJsonSpec extends Specification {

  def "create should not return null"() {
    CollectionJson collectionJson = new CollectionJson()

    expect:
    collectionJson.create() != null
  }

}
