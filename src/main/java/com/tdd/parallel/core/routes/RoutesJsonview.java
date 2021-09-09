package com.tdd.parallel.core.routes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutesJsonview {
  public static final String REQ_MAP_JV = "/personTemplJview";

  public static final String CRUD_JV_ADMIN = "/personCrudJviewAdmin";
  public static final String CRUD_JV_USER = "/personCrudJviewUser";

  public static final String REPO_JV_ADMIN= "/personRepoJviewAdmin";
  public static final String REPO_JV_USER= "/personRepoJviewUser";

  public static final String TPL_JV_ADMIN = "/personTemplJviewAdmin";
  public static final String TPL_JV_USER = "/personTemplJviewUser";
  public static final String ID_JV = "/{id}";
}
