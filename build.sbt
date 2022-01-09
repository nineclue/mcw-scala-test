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

Compile / npmDependencies ++= Seq(
    "@material/button" -> "13.0.0",
)