name := "paypal"

organization := "net.liftmodules"

version := "1.3-SNAPSHOT"

liftVersion <<= liftVersion ?? "2.6-SNAPSHOT"

liftEdition <<= liftVersion apply { _.substring(0,3) }

moduleName <<= (name, liftEdition) { (n, e) =>  n + "_" + e }

scalaVersion := "2.10.3"

crossScalaVersions := Seq("2.11.2", "2.10.4", "2.9.2", "2.9.1-1", "2.9.1")

scalacOptions ++= Seq("-unchecked", "-deprecation")

resolvers += "CB Central Mirror" at "http://repo.cloudbees.com/content/groups/public"

resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

libraryDependencies <++= liftVersion { v =>
  "net.liftweb" %% "lift-webkit" % v % "provided" ::
  Nil
}

libraryDependencies += "commons-httpclient" % "commons-httpclient" % "3.1"

libraryDependencies <+= scalaVersion { sv => sv match {
  case "2.9.2" | "2.9.1" | "2.9.1-1" => "org.specs2" %% "specs2" % "1.12.3" % "test"
  case "2.10.4"                      => "org.specs2" %% "specs2" % "1.13"   % "test"
  case _                             => "org.specs2" %% "specs2" % "2.3.11" % "test"
 }
}

publishTo <<= version { _.endsWith("SNAPSHOT") match {
 	case true  => Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
 	case false => Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
  }
 }


// For local deployment:

credentials += Credentials( file("sonatype.credentials") )

// For the build server:

credentials += Credentials( file("/private/liftmodules/sonatype.credentials") )

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
	<url>https://github.com/liftmodules/paypal</url>
	<licenses>
		<license>
	      <name>Apache 2.0 License</name>
	      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
	      <distribution>repo</distribution>
	    </license>
	 </licenses>
	 <scm>
	    <url>git@github.com:liftmodules/paypal.git</url>
	    <connection>scm:git:git@github.com:liftmodules/paypal.git</connection>
	 </scm>
	 <developers>
	    <developer>
	      <id>liftmodules</id>
	      <name>Lift Team</name>
	      <url>http://www.liftmodules.net</url>
	 	</developer>
	 </developers>
 )
