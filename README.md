#Catatumbo - Object Mapping and Persistence Framework for Google Cloud Datastore 
Catatumbo is an Open Source persistence framework for mapping Java objects (POJOs) to 
[Google Cloud Datastore](https://cloud.google.com/datastore/) and vice versa. 

Catatumbo framework is built on top of 
[gcloud-java-datasource API](https://github.com/GoogleCloudPlatform/gcloud-java/tree/master/gcloud-java-datastore). 
The framework provides a handful of annotations to make your model classes manageable by the framework. In other words, 
Catatumbo is similar to JPA (Java Persistence API), but is specifically designed to work with Google Cloud Datastore 
instead of Relational Databases.  

> **Note: Catatumbo is a work-in-progress and future changes are not guaranteed to be backward compatible**

##Features
* Automatic mapping of model classes (POJOs) to Cloud Datastore Entities and vice versa
* Automatic generation of Identifiers (for both Numeric and String types)  
* Support for a variety of Data Types: 
	* boolean, Boolean 
	* char, Character 
	* short, Short 
	* int, Integer 
	* long, Long 
	* float, Float 
	* double, Double 
	* String 
	* Byte Arrays 
	* Char Arrays 
	* Date 
	* Calendar 
	* Geo Location 
	* Keys, Parent Keys and Key References
	* List Values 
* Support for embedded objects through the use of Embedded and Embeddable annotations. 
* Create, Retrieve, Update, Delete, Upsert (update or insert)  
* Support for executing GQL Queries. This includes:  
	* Entity queries 
	* Projection queries 
	* Key-only queries 
* Transaction Support  

##Quick Start Guide 
Please visit [http://www.jMethods.com/catatumbo/quick-start.html](http://www.jMethods.com/catatumbo/quick-start.html)

##API Documentation 
Online documentation (Javadocs) for Catatumbo API is available at [http://www.jmethods.com/catatumbo/api-documentation.html](http://www.jmethods.com/catatumbo/api-documentation.html). 