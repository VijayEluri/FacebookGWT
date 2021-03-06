/*
 * Copyright (C) 2010 deNormans
 * http://www.denormans.com/
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of deNormans ("Confidential Information"). You 
 * shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with deNormans.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * DENORMANS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.denormans.facebookgwt.api.client.auth;

import com.denormans.facebookgwt.api.client.common.FBJSObject;
import com.denormans.facebookgwt.api.shared.FBEnum;
import com.denormans.facebookgwt.api.shared.auth.FBPermission;
import com.denormans.facebookgwt.api.shared.auth.FBPermissions;

import com.google.gwt.core.client.JsArrayString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Permissions extends FBJSObject {
  protected Permissions() {
  }

  public static List<FBPermission> parseApiValues(final String permissionApiValues) {
    if (permissionApiValues == null || permissionApiValues.trim().length() == 0) {
      return Collections.emptyList();
    }

    if (permissionApiValues.trim().charAt(0) != '{') {
      return FBPermissions.valuesFromApiValues(FBEnum.Util.splitApiValues(permissionApiValues));
    }

    Permissions permissions = fromJSONString(permissionApiValues);
    if (permissions == null) {
      return Collections.emptyList();
    }

    return permissions.getAllPermissions();
  }

  public final List<FBPermission> getAllPermissions() {
    List<FBPermission> allPermissions = new ArrayList<FBPermission>();
    allPermissions.addAll(getExtendedPermissions());
    allPermissions.addAll(getUserPermissions());
    allPermissions.addAll(getFriendPermissions());
    return allPermissions;
  }

  public final List<FBPermission> getExtendedPermissions() {
    return FBPermissions.valuesFromApiValues(convertJsArrayStringToList(getExtendedApiPermissionsJS()));
  }

  private native JsArrayString getExtendedApiPermissionsJS() /*-{
    return this.extended || [];
  }-*/;

  public final List<FBPermission> getUserPermissions() {
    return FBPermissions.valuesFromApiValues(convertJsArrayStringToList(getUserApiPermissionsJS()));
  }

  private native JsArrayString getUserApiPermissionsJS() /*-{
    return this.user || [];
  }-*/;

  public final List<FBPermission> getFriendPermissions() {
    return FBPermissions.valuesFromApiValues(convertJsArrayStringToList(getFriendApiPermissionsJS()));
  }

  private native JsArrayString getFriendApiPermissionsJS() /*-{
    return this.friends || [];
  }-*/;
}
