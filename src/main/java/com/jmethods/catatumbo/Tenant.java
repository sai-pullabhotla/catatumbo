/*
 * Copyright 2016 Sai Pullabhotla.
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

/**
 * Used for setting/getting the namespace of current thread. This class maintains a
 * {@link ThreadLocal} to store the current namespace. By default, the namespace is
 * <code>null</code>.
 * <p>
 * A typical use of this class in a web application involves creation of a ServletFilter that sets
 * the namespace on each request processing thread. Below is an example filter that sets the
 * namespace to the logged in user's name:
 * </p>
 * 
 * <pre>
 * import java.io.IOException;
 * import java.security.Principal;
 * 
 * import javax.servlet.Filter;
 * import javax.servlet.FilterChain;
 * import javax.servlet.FilterConfig;
 * import javax.servlet.ServletException;
 * import javax.servlet.ServletRequest;
 * import javax.servlet.ServletResponse;
 * import javax.servlet.annotation.WebFilter;
 * import javax.servlet.http.HttpServletRequest;
 * 
 * import com.jmethods.catatumbo.Tenant;
 * 
 * &#64;WebFilter("/*")
 * public class NamespaceFilter implements Filter {
 * 
 *   &#64;Override
 *   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
 *       throws IOException, ServletException {
 *     HttpServletRequest httpServletRequest = (HttpServletRequest) request;
 *     String namespace = getCurrentUser(httpServletRequest);
 *     Tenant.setNamespace(namespace);
 *     chain.doFilter(request, response);
 *   }
 * 
 *   // Replace the contents of this method to return the appropriate
 *   // namespace. For example, you could use a domain based namespace.
 *   private static String getCurrentUser(HttpServletRequest request) {
 *     Principal principal = request.getUserPrincipal();
 *     if (principal != null) {
 *       return principal.getName();
 *     }
 *     return null;
 *   }
 * 
 *   &#64;Override
 *   public void destroy() {
 *   }
 * 
 *   &#64;Override
 *   public void init(FilterConfig fConfig) throws ServletException {
 *   }
 * 
 * }
 * 
 * </pre>
 * 
 * @author Sai Pullabhotla
 *
 */
public class Tenant {

  /**
   * ThreadLocal for storing the namespace
   */
  private static ThreadLocal<String> threadNamespace = new ThreadLocal<>();

  /**
   * Returns the namespace.
   * 
   * @return the namespace. May return <code>null</code>.
   */
  public static String getNamespace() {
    return threadNamespace.get();
  }

  /**
   * Sets the namespace to the given value. Set to <code>null</code> to use the namespace with which
   * the {@link EntityManager} was created. An empty string sets the namespace to the default
   * namespace.
   * 
   * @param namespace
   *          the namespace to set.
   */
  public static void setNamespace(String namespace) {
    threadNamespace.set(namespace);
  }

}
