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
package cz.koto.misak.kotipoint.android.mobile.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author e.matsyuk
 */
public abstract class AutoLoadingRecyclerViewAdapter<T,U extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<U> {

    protected List<T> mElementList = new ArrayList<>();

    protected int mFooterCount = 0;

    public void addNewItems(List<T> items) {
        mElementList.addAll(mElementList.size()- mFooterCount,items);
    }

    public void addNewItem(T item) {
        mElementList.add(mElementList.size()- mFooterCount,item);
    }

    public void addFooter(T item) {
        mElementList.add(item);
        mFooterCount++;
    }

    public List<T> getItems() {
        return mElementList;
    }

    public T getItem(int position) {
        return mElementList.get(position);
    }

    @Override
    public int getItemCount() {
        return mElementList.size();
    }

    public abstract void hideAutoLoader();

    public abstract void showLoader();


    abstract void addDefaultItem();
    /* SWIPE 2 REFRESH methods */

    // Clean all elements of the recycler
    public void clearViewItems() {
        mFooterCount = 0;
        mElementList.clear();
        addDefaultItem();
        notifyDataSetChanged();
    }
}
