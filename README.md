Catatumbo - JPA like Persistence Framework for Google Cloud Datastore
===================================================================== 
Catatumbo is an Open Source, JPA like persistence framework for mapping and persisting domain model to 
[Google Cloud Datastore](https://cloud.google.com/datastore/). 

Catatumbo framework is built on top of 
[Google Cloud Java Client for Datastore](https://github.com/GoogleCloudPlatform/google-cloud-java/tree/master/google-cloud-datastore). 
The framework provides a handful of annotations to make your model classes manageable by the framework. In other words, 
Catatumbo is similar to JPA (Java Persistence API), but is specifically designed to work with Google Cloud Datastore 
instead of Relational Databases.  

Features
--------
* Automatic mapping of model classes (POJOs) to Cloud Datastore Entities and vice versa. Model classes may follow the below design patterns: 
	* Classic Java Beans Pattern - with a default constructor, accessor (getter) and mutator (setter) methods for each persistable field 
	* Builder Pattern - for immutable objects or to ensure object's state is always valid 
* Automatic generation of Identifiers (for both Numeric and String types)  
* Support for strongly typed Identifiers (e.g. a custom class UserId that wraps a numeric/string ID) 
* Ignore specific fields of your model object from persistence operations 
* Support for a variety of Data Types: 
	* boolean, Boolean 
	* char, Character 
	* short, Short 
	* int, Integer 
	* long, Long 
	* float, Float 
	* double, Double 
	* BigDecimal 
	* String 
	* Enum 
	* Byte Arrays 
	* Char Arrays 
	* java.util.Date 
	* java.util.Calendar 
	* java.time.LocalDate 
	* java.time.LocalTime 
	* java.time.LocalDateTime 
	* java.time.OffsetDateTime 
	* java.time.ZonedDateTime  
	* Geo Location 
	* Keys, Parent Keys and Key References
	* List Values (List & Set) 
	* Maps 
	* Map of Maps 
* Support for "true" Decimal types, though the Cloud Datastore does not have native support for this. 
* Ability to annotate which fields are indexed/not indexed 
* Ability to annotate optional fields. Optional fields are omitted from persistence when their value is null. 
* Support for secondary indexes to index a property for case-insensitive querying/sorting. 
* Ability to attach custom mappers to a field to map its value to/from the Datastore. 
* Ability to override default Mappers for a specified Type/Class. 
* Support for embedded objects through the use of @Embedded and @Embeddable annotations. 
* Persisting of inherited fields of an Entity through the use of @MappedSuperClass annotation. 
* Optimistic Locking support through the use of @Version annotation. 
* Automatic timestamping (creation and modification) of entities using the @CreatedTimestamp and @UpdatedTimestamp annotations. 
* Support for multitenancy - ability to work with multiple namespaces. 
* Support for entity life cycle listeners (e.g. PreInsert, PostInsert callbacks) 
* Create, Retrieve, Update, Delete, Upsert (update or insert)  
* Support for executing GQL Queries. This includes:  
	* Entity queries 
	* Projection queries 
	* Key-only queries 
* Support for Batch Write operations 
* Transaction Support 
* Metadata API for retrieving the Datastore metadata 
* Programmatic access to Datastore Statistics 
* Ability to work with Local Datastore (Datastore Emulator) during development phase   

Project's Web Site
------------------
[http://catatumbo.io](http://catatumbo.io) 

Quick Start Guide
----------------- 
Please visit [http://catatumbo.io/quick-start.html](http://catatumbo.io/quick-start.html)

Video Tutorials
--------------- 
Please visit [http://catatumbo.io/video-tutorials.html](http://catatumbo.io/video-tutorials.html)

API Documentation
----------------- 
Online documentation (Javadocs) for Catatumbo API is available at [http://catatumbo.io/api-docs.html](http://catatumbo.io/api-docs.html). 