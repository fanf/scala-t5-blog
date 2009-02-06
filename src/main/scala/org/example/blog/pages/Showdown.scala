package org.example.blog.pages

import org.apache.tapestry5.annotations.{IncludeJavaScriptLibrary,IncludeStylesheet}

@IncludeJavaScriptLibrary(Array("context:script/showdown.js","context:script/showdown-gui.js"))
@IncludeStylesheet(Array("context:css/showdown.css"))
class Showdown {

}
