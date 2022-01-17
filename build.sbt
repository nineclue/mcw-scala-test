scalaVersion := "3.1.0"

enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)

scalaJSUseMainModuleInitializer := true

// scalaJSMainModuleInitializer := Some("App")
Compile / mainClass := Some("App")

scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }

libraryDependencies ++= Seq(
    "com.raquo" %%% "laminar" % "0.13.1",
)

val materialVersion = "13.0.0"
Compile / npmDependencies ++= Seq(
    "@material/button" -> materialVersion,
    "@material/top-app-bar" -> materialVersion,
    "@material/list" -> materialVersion,
    "@material/drawer" -> materialVersion,
    "@material/textfield" -> materialVersion,
)