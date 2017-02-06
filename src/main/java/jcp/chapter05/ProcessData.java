package jcp.chapter05;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 10-19
 */
public class ProcessData {
    private static final ReentrantReadWriteLock rwl       = new ReentrantReadWriteLock();
    private static final Lock                   readLock  = rwl.readLock();
    private static final Lock                   writeLock = rwl.writeLock();
    private volatile boolean                    update    = false;

    public void processData() {
        readLock.lock();
        if (!update) {
            // �������ͷŶ���
            readLock.unlock();
            // ��������д����ȡ����ʼ
            writeLock.lock();
            try {
                if (!update) {
                    // ׼�����ݵ����̣��ԣ�
                    update = true;
                }
                readLock.lock();
            } finally {
                writeLock.unlock();
            }
            // ��������ɣ�д������Ϊ����
        }
        try {
            // ʹ�����ݵ����̣��ԣ�
        } finally {
            readLock.unlock();
        }
    }

}
