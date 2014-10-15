import groovy.json.JsonBuilder

class CollectionJson {
  def create() {
    JsonBuilder jsonBuilder = new JsonBuilder()
    def root = jsonBuilder.collection{
        version null
        href null
    }

    return jsonBuilder.toString()
  }
}
