
package me.pjq.database;

/**
 * The database interface.Right now it almost useless.Add some common methods
 * here. <br>
 * If need support both Mysql and Sqlite,maybe need it,and implements it.
 * 
 * @author pjq0274
 */
public interface DataBaseInterface {
    /**
     * The Database init,such as create the tables.
     */
    boolean init();

    void query();
}
