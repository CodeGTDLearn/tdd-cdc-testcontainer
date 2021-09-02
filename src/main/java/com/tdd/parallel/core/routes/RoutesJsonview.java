package com.tdd.parallel.core.routes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutesJsonview {
  public static final String CRUD_REPO_REQMAP_JSONVIEW = "/personCrudRepoJsonview";
  public static final String CRUD_REPO_ADMIN_JSONVIEW = "/personCrudRepoJsonview/admin";
  public static final String CRUD_REPO_USER_JSONVIEW = "/personCrudRepoJsonview/user";
  public static final String ADMIN_ID_JSONVIEW = "/admin/{id}";
  public static final String USER_ID_JSONVIEW = "/admin/{id}";
  public static final String ID_JSONVIEW = "/{id}";

  public static final String ROUTE_MGO_REPO_JSONVIEW = "/personMongoRepoJsonview";
  public static final String ID_MGO_REPO_JSONVIEW = "/{id}";

  public static final String ROUTE_RCT_MGO_TPL_REPO_JSONVIEW = "/personReactMongoTemplRepoJsonview";
  public static final String ID_RCT_MGO_TPL_REPO_JSONVIEW = "/{id}";

  public static final String REQ_MAP_TPL_REPO_JSONVIEW = "/personTemplJsonview";
  public static final String ID_TPL_REPO_JSONVIEW = "/{id}";
}
