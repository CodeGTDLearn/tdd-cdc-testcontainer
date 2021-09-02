package com.tdd.parallel.core.routes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutesStandard {
  public static final String CRUD_REPO_STD = "/personCrudRepoStandard";
  public static final String ID_STD = "/{id}";

  public static final String MGO_REPO_STD = "/personMongoRepoStandard";
//  public static final String ID_MGO_REPO_STD = "/{id}";

  public static final String RCT_MGO_TPL_REPO_STD = "/personReactMongoTemplRepoStandard";
//  public static final String ID_RCT_MGO_TPL_REPO_STD = "/{id}";

  public static final String REQ_MAP_TPL_REPO_STD = "/personTemplStandard";
//  public static final String ID_TPL_REPO_STD = "/{id}";
}
