package fitprogwork.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.omstu.figprogwork.db.DbCacheService;

@Component
public class CacheCleanupScheduler {

    private static final Logger log = LoggerFactory.getLogger(CacheCleanupScheduler.class);
    private final DbCacheService dbCacheService;

    public CacheCleanupScheduler(DbCacheService dbCacheService) {
        this.dbCacheService = dbCacheService;
    }

    @Scheduled(fixedDelay = 30000)
    public void deleteOldEntries() {
        try {
            log.info("Scheduler: deleting entries older than 60 seconds");
            dbCacheService.deleteOlderThan(60);
        } catch (Exception e) {
            log.error("Scheduler error during cleanup, closing connection: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void deleteAllEntriesWeekly() {
        try {
            log.info("Scheduler: weekly full cache clear");
            dbCacheService.deleteAll();
        } catch (Exception e) {
            log.error("Scheduler error during weekly cleanup: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}