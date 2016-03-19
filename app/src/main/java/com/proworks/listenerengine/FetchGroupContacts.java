package com.proworks.listenerengine;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Sharad on 05-03-2016.
 */
public class FetchGroupContacts {
    Context context;

    private ArrayList<Item> fetchGroups() {
        ArrayList<Item> groupList = new ArrayList<Item>();
        String[] projection = new String[]{ContactsContract.Groups._ID, ContactsContract.Groups.TITLE};
        Cursor cursor = context.getContentResolver().query(ContactsContract.Groups.CONTENT_URI,
                projection, null, null, null);
        ArrayList<String> groupTitle = new ArrayList<String>();
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.id = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups._ID));
            String groupName = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE));

            if (groupName.contains("Group:"))
                groupName = groupName.substring(groupName.indexOf("Group:") + "Group:".length()).trim();

            if (groupName.contains("Favorite_"))
                groupName = "Favorite";

            if (groupName.contains("Starred in Android") || groupName.contains("My Contacts"))
                continue;

            if (groupTitle.contains(groupName)) {
                for (Item group : groupList) {
                    if (group.name.equals(groupName)) {
                        group.id += "," + item.id;
                        break;
                    }
                }
            } else {
                groupTitle.add(groupName);
                item.name = groupName;
                groupList.add(item);
            }

        }

        cursor.close();
        Collections.sort(groupList, new Comparator<Item>() {
            public int compare(Item item1, Item item2) {
                return item2.name.compareTo(item1.name) < 0
                        ? 0 : -1;
            }
        });
        return groupList;
    }


}
