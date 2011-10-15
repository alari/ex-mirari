@Typed package mirari

/**
 * @author Dmitry Kurinskiy
 * @since 19.08.11 13:44
 */
class ServiceResponse {
  boolean ok
  String messageCode
  String redirectUri
  def model

  ServiceResponse() {}

  /**
   * @attr ok
   * @param attributes
   */
  ServiceResponse(Map attributes) {
    setAttributes(attributes)
  }

  ServiceResponse setAttributes(Map attributes) {
    if (attributes.containsKey("ok")) ok = attributes.ok
    if (attributes.containsKey("messageCode")) messageCode = attributes.messageCode
    if (attributes.containsKey("redirectUri")) redirectUri = attributes.redirectUri
    if (attributes.containsKey("model")) model = attributes.model
    this
  }
}
