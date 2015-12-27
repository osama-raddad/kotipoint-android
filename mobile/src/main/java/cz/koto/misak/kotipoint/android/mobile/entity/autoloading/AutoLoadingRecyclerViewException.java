/**
 * Copyright 2015 Eugene Matsyuk (matzuk2@mail.ru)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package cz.koto.misak.kotipoint.android.mobile.entity.autoloading;

/**
 * @author e.matsyuk
 */
public class AutoLoadingRecyclerViewException extends RuntimeException {

    public AutoLoadingRecyclerViewException() {
        super("Exception in AutoLoadingRecyclerView");
    }

    public AutoLoadingRecyclerViewException(String detailMessage) {
        super(detailMessage);
    }
}
