package com.airfont.kidspassport;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ctslin on 12/5/14.
 */
@Table(name = "Child")
public class Child extends Model {
  @Column(name = "Fullname")
  public String fullname;
  @Column(name = "Nickname")
  public String nickname;
  @Column(name = "Gender")
  public String gender;

  public static Child getRandom() {
    return new Select().from(Child.class).orderBy("RANDOM()").executeSingle();
  }

  public static List<Child> getAll() {
    return new Select()
      .from(Child.class)
      //.where("Category = ?", category.getId())
      .orderBy("Fullname ASC")
      .execute();
  }

  public static int count() {
    Cursor c = Cache.openDatabase().query("Child", null, null, null, null, null, null);
    int count = c.getColumnCount();
    c.close();
    return count;
  }
}
