package org.example.blog.components


import org.apache.tapestry5._
import org.apache.tapestry5.annotations._
import org.apache.tapestry5.ioc.AnnotationProvider
import org.apache.tapestry5.ioc.annotations._

import org.apache.tapestry5.RenderSupport

import org.apache.tapestry5.corelib.components.TextArea

class ShowdownTextarea {

	/**
	* The value to be read and updated. This is not necessarily a string, a translator may be provided to convert
	* between client side and server side representations. If not bound, a default binding is made to a property of the
	* container matching the component's id. If no such property exists, then you will see a runtime exception due to
	* the unbound value parameter.
	*/
	@Parameter{val required = true, val principal = true}
	var value : Object = _

	/**
	* The object which will perform translation between server-side and client-side representations. If not specified,
	* a value will usually be generated based on the type of the value parameter.
	*/
	@Parameter{val allowNull = false, val defaultPrefix = BindingConstants.TRANSLATE}
	var translate : FieldTranslator[Object]  = _

	/**
	* The object that will perform input validation (which occurs after translation). The validate binding prefix is
	* generally used to provide this object in a declarative fashion.
	*/
	@Parameter{val defaultPrefix = BindingConstants.VALIDATE}
	var validate : FieldValidator[Object] = _

	/**
	* Provider of annotations used for some defaults.  Annotation are usually provided in terms of the value parameter
	* (i.e., from the getter and/or setter bound to the value parameter).
	*
	* @see org.apache.tapestry5.beaneditor.Width
	*/
	@Parameter
	var annotationProvider: AnnotationProvider = _

	/**
	* Defines how nulls on the server side, or sent from the client side, are treated. The selected strategy may
	* replace the nulls with some other value. The default strategy leaves nulls alone.  Another built-in strategy,
	* zero, replaces nulls with the value 0.
	*/
	@Parameter{val defaultPrefix = BindingConstants.NULLFIELDSTRATEGY, val value = "default"}
	var nulls:NullFieldStrategy = _

      
    @Component{ val parameters = Array(
      "value=inherit:value",
      "translate=inherit:translate",
      "validate=inherit:validate",
      "annotationProvider=inherit:annotationProvider",
      "nulls=inherit:nulls"
    )}
    var plop : TextArea = _
 
    
    @Property{val write=false}
    var clientId = ""
    
    @Inject
    var renderSupport : RenderSupport = _
    
    def setupRender = {
      this.clientId = renderSupport.allocateClientId("showdown");
    }
    
}
