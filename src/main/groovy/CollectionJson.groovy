import groovy.json.JsonBuilder

class CollectionJson {
  def version
  def href
  def items = []

  String create() {
    JsonBuilder jsonBuilder = new JsonBuilder()
    jsonBuilder(collection : this)
    jsonBuilder.toString()
  }

  def populateItems(Items item) {
    items << item
  }
}

class Items {


}
