package com.tdd.parallel.core.routes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutesJsonview {
  public static final String JV_REQ_MAP = "/personTemplJview";

  public static final String JV_CRUD_ADMIN_POST_REQUEST = "/personCrudJviewAdminRequestPost";
  public static final String JV_CRUD_ADMIN = "/personCrudJviewAdmin";
  public static final String JV_CRUD_USER = "/personCrudJviewUser";
  public static final String JV_CRUD_DEL = "/personCrudJviewDel";

  public static final String JV_REPO_ADMIN_POST_REQUEST = "/personRepoJviewAdminRequestPost";
  public static final String JV_REPO_ADMIN = "/personRepoJviewAdmin";
  public static final String JV_REPO_USER = "/personRepoJviewUser";
  public static final String JV_REPO_DEL = "/personRepoJviewDel";

  public static final String JV_TEMPL_ADMIN_POST_REQUEST = "/personTemplJviewAdminRequestPost";
  public static final String JV_TEMPL_ADMIN = "/personTemplJviewAdmin";
  public static final String JV_TEMPL_USER = "/personTemplJviewUser";
  public static final String JV_TEMPL_DEL = "/personTemplJviewDel";

  public static final String JV_ID = "/{id}";
}
