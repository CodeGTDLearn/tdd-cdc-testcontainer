package com.tdd.parallel.core.routes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutesJsonview {
  public static final String CRUD_REPO_JVIEW_ADMIN = "/personCrudRepoJsonviewAdmin";
  public static final String CRUD_REPO_JVIEW_USER = "/personCrudRepoJsonviewUser";
  public static final String ID_JVIEW = "/{id}";
  public static final String MGO_REPO_JVIEW = "/personMongoRepoJsonview";
  public static final String RCT_MGO_TPL_REP_JVIEW = "/personReactMongoTemplRepoJsonview";
  public static final String REQ_MAP_TPL_REPO_JVIEW = "/personTemplJsonview";
}
