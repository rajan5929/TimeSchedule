package greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import greendao.TSBox;

import greendao.TSBoxDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig tSBoxDaoConfig;

    private final TSBoxDao tSBoxDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        tSBoxDaoConfig = daoConfigMap.get(TSBoxDao.class).clone();
        tSBoxDaoConfig.initIdentityScope(type);

        tSBoxDao = new TSBoxDao(tSBoxDaoConfig, this);

        registerDao(TSBox.class, tSBoxDao);
    }
    
    public void clear() {
        tSBoxDaoConfig.getIdentityScope().clear();
    }

    public TSBoxDao getTSBoxDao() {
        return tSBoxDao;
    }

}
