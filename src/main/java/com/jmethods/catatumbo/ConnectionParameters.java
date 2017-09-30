/*
 * Copyright 2017 Sai Pullabhotla.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jmethods.catatumbo;

import java.io.File;
import java.io.InputStream;

import com.google.auth.http.HttpTransportFactory;
import com.google.datastore.v1.client.DatastoreFactory;

/**
 * Objects of this class contain various parameters that are needed for connecting to the Datastore.
 * Instances of this class can be sent to {@link EntityManagerFactory} to create an
 * {@link EntityManager}.
 * 
 * <p>
 * <strong>Note: </strong> <br>
 * Credentials can be specified using one of the <code>setJsonCredentials*</code> methods. If
 * credentials are set using both an <code>InputStream</code> and <code>File</code> or
 * <code>Path</code>, the stream will be used for authentication.
 * </p>
 * 
 * <p>
 * <strong>Connecting to the Datastore Emulator: </strong><br>
 * Call the {@link ConnectionParameters#setServiceURL(String)} method to connect to the Datastore
 * Emulator. For example, to connect to the Datastore Emulator running on port 9999 on
 * <code>localhost</code>, call <code>setServiceURL("http://localhost:9999");</code>.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
public class ConnectionParameters {

  /**
   * Default Service URL for connecting to Datastore on GCP.
   */
  public static final String DEFAULT_SERVICE_URL = DatastoreFactory.DEFAULT_HOST;

  /**
   * Default connection timeout
   */
  public static final int DEFAULT_CONNECTION_TIMEOUT = 20000;

  /**
   * Default read timeout
   */
  public static final int DEFAULT_READ_TIMEOUT = 20000;

  /**
   * Service URL
   */
  private String serviceURL;

  /**
   * Datastore namespace
   */
  private String namespace;

  /**
   * Project ID
   */
  private String projectId;

  /**
   * JSON credentials file
   */
  private File jsonCredentialsFile;

  /**
   * JSON credentials for authentication
   */
  private InputStream jsonCredentialsStream;

  /**
   * Connection timeout
   */
  private int connectionTimeout;

  /**
   * Read timeout
   */
  private int readTimeout;

  /**
   * HTTP Transport Factory
   */
  private HttpTransportFactory httpTransportFactory;

  /**
   * Creates a new instance of <code>ConnectionParameters</code>.
   */
  public ConnectionParameters() {
    this.serviceURL = DEFAULT_SERVICE_URL;
    this.connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
    this.readTimeout = DEFAULT_READ_TIMEOUT;
  }

  /**
   * Returns the service URL.
   * 
   * @return the service URL.
   */
  public String getServiceURL() {
    return serviceURL;
  }

  /**
   * Sets the service URL. The service URL is typically of the form -
   * <code>http[s]://host[:port]</code>. A <code>null</code> or empty string would result in
   * connecting to the default Datastore on GCP. All other values, except
   * {@link ConnectionParameters#DEFAULT_SERVICE_URL}, would be interpreted as a connection to the
   * Datastore Emulator.
   * 
   * @param serviceURL
   *          the serviceURL to set.
   */
  public void setServiceURL(String serviceURL) {
    if (Utility.isNullOrEmpty(serviceURL)) {
      this.serviceURL = DEFAULT_SERVICE_URL;
    } else {
      this.serviceURL = serviceURL;
    }
  }

  /**
   * Returns the Datastore namespace.
   * 
   * @return the Datastore namespace.
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Sets the Datastore namespace.A <code>null</code> value would be interpreted as default
   * namespace.
   * 
   * @param namespace
   *          the Datastore namespace.
   */
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  /**
   * Returns the Project ID.
   * 
   * @return the Project ID.
   */
  public String getProjectId() {
    return projectId;
  }

  /**
   * Sets the Project ID.A <code>null</code> value would use the default project ID, if it can be
   * determined from the environment.
   * 
   * @param projectId
   *          the Project ID.
   */
  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  /**
   * Returns the JSON credentials file.
   *
   * @return the JSON credentials file.
   */
  public File getJsonCredentialsFile() {
    return jsonCredentialsFile;
  }

  /**
   * Sets the JSON credentials file.
   * 
   * @param jsonCredentialsFile
   *          the JSON credentials file.
   */
  public void setJsonCredentialsFile(File jsonCredentialsFile) {
    this.jsonCredentialsFile = jsonCredentialsFile;
  }

  /**
   * Sets the JSON credentials path.
   * 
   * @param jsonCredentialsPath
   *          the JSON credentials path.
   */
  public void setJsonCredentialsFile(String jsonCredentialsPath) {
    if (!Utility.isNullOrEmpty(jsonCredentialsPath)) {
      setJsonCredentialsFile(new File(jsonCredentialsPath));
    } else {
      setJsonCredentialsFile((File) null);
    }
  }

  /**
   * Returns an InputStream that contains the JSON credentials.
   * 
   * @return an InputStream that contains the JSON credentials.
   */
  public InputStream getJsonCredentialsStream() {
    return jsonCredentialsStream;
  }

  /**
   * Sets the JSON credentials.
   * 
   * @param jsonCredentialsStream
   *          An {@link InputStream} containing the JSON credentials.
   */
  public void setJsonCredentialsStream(InputStream jsonCredentialsStream) {
    this.jsonCredentialsStream = jsonCredentialsStream;
  }

  /**
   * Returns the connection timeout, in milliseconds.
   * 
   * @return the connection timeout, in milliseconds.
   */
  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  /**
   * Sets the connection timeout, in milliseconds. A value of zero is interpreted as infinite
   * timeout. A negative value implies to use the default timeout.
   * 
   * @param connectionTimeout
   *          the connection timeout, in milliseconds.
   */
  public void setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout < 0 ? DEFAULT_CONNECTION_TIMEOUT : connectionTimeout;
  }

  /**
   * Returns the read timeout, in milliseconds.
   * 
   * @return the read timeout, in milliseconds.
   */
  public int getReadTimeout() {
    return readTimeout;
  }

  /**
   * Sets the read timeout, in milliseconds. A value of zero is interpreted as infinite timeout. A
   * negative value implies to use the default timeout.
   * 
   * @param readTimeout
   *          the read timeout, in milliseconds.
   */
  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout < 0 ? DEFAULT_READ_TIMEOUT : readTimeout;
  }

  /**
   * Returns the HttpTransportFactory.
   * 
   * @return the HttpTransportFactory.
   */
  public HttpTransportFactory getHttpTransportFactory() {
    return httpTransportFactory;
  }

  /**
   * Sets the HttpTransportFactory.
   * 
   * @param httpTransportFactory
   *          the HttpTransportFactory
   */
  public void setHttpTransportFactory(HttpTransportFactory httpTransportFactory) {
    this.httpTransportFactory = httpTransportFactory;
  }

  /**
   * Tells whether or not these connection parameters indicate a connection to a Datastore Emulator.
   * 
   * @return <code>true</code>, if these connection parameters indicate a connection to the
   *         Datastore Emulator; <code>false</code>, otherwise.
   */
  public boolean isEmulator() {
    return !DEFAULT_SERVICE_URL.equals(serviceURL);
  }

  @Override
  public String toString() {
    return "ConnectionParameters [serviceURL=" + serviceURL + ", namespace=" + namespace
        + ", projectId=" + projectId + ", jsonCredentialsFile=" + jsonCredentialsFile
        + ", jsonCredentialsStream=" + jsonCredentialsStream + ", connectionTimeout="
        + connectionTimeout + ", readTimeout=" + readTimeout + ", httpTransportFactory="
        + httpTransportFactory + ", emulator=" + isEmulator() + "]";
  }

}
