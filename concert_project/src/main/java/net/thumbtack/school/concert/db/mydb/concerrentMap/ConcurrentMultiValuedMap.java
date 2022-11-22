package net.thumbtack.school.concert.db.mydb.concerrentMap;


import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentMultiValuedMap<A, B> {
    private ArrayListValuedHashMap<A, B>  listValuedHashMap;
    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    public ConcurrentMultiValuedMap(ArrayListValuedHashMap<A, B> listValuedHashMap) {
        this.listValuedHashMap = listValuedHashMap;
    }

    public int size() {
        int size;
        try {
            lock.readLock().lock();
            size = listValuedHashMap.size();
        } finally {
            lock.readLock().unlock();
        }
        return size;
    }


    public List<B> get(A a) {
        List<B> b;
        try {
            lock.readLock().lock();
            b = listValuedHashMap.get(a);
        } finally {
            lock.readLock().unlock();
        }
        return b;
    }

    public boolean put(A a, B b) {
        try {
            lock.writeLock().lock();
            return listValuedHashMap.put(a, b);
        } finally {
            lock.writeLock().unlock();
        }
    }


    public List<B> remove(Object o) {
        try {
            lock.writeLock().lock();
            return listValuedHashMap.remove(o);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean removeMapping(Object o, Object o1) {
        try {
            lock.writeLock().lock();
            return listValuedHashMap.removeMapping(o, o1);
        } finally {
            lock.writeLock().unlock();
        }
    }


    public void clear() {
        try {
            lock.writeLock().lock();
            listValuedHashMap.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }


    public Collection<B> values() {
        try {
            lock.readLock().lock();
            return listValuedHashMap.values();
        } finally {
            lock.readLock().unlock();
        }
    }

}
