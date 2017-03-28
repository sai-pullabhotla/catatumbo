Releasing Catatumbo
===================

Maven Settings
--------------

Update the ~/.m2/settings.xml to have the following settings. 

```xml

	<settings>
	  <profiles>
	    <profile>
	      <id>ossrh</id>
	      <activation>
	        <activeByDefault>true</activeByDefault>
	      </activation>
	      <properties>
	      	<!-- Update the path to the GPG executable -->
	        <gpg.executable>/usr/local/bin/gpg</gpg.executable>
	        <!-- Update Passphrase to the PGP signing key -->
	        <gpg.passphrase>YOUR_PGP_KEY_PASSPHRASE</gpg.passphrase>
	      </properties>
	    </profile>
	  </profiles>
	  <servers>
	    <server>
	      <id>ossrh</id>
	      <!-- Update username and password for Sonatype. Use the token -->
	      <username>YOUR_USERNAME</username>
	      <password>YOUR_PASSWORD</password>
	    </server>
	    <server>
	      <id>scm</id>
	      <username>SCM_USERNAME</username>
	      <password>SCM_PASSWORD</password>
	    </server>
	  </servers>
	</settings>

```

Procedure
---------

1. Make sure all changes are checked into SCM. 
2. Run all JUnit tests and make sure there are no failures 
3. Run `mvn release:clean release:prepare`. This will update the version in the POM, checks in the POM to SCM and created a tag.
4. If any issues are encountered during the above step, undo any changes by running `mvn release:rollback`.   
5. Assuming step 3 succeeded, run `mvn release:perform` to complete the release. This will create the artifacts from the previously tagged version and uploads them to staging repository. 
6. Login to [https://oss.sonatype.org](https://oss.sonatype.org) and verify the artifacts. 
7. If all looks good, release the artifacts to be published to Maven Central. 
8. After a few hours, check [Maven Central](http://search.maven.org) and ensure that the new version is available.  

 
