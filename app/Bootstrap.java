import play.*;
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        //Check if database is empty
        if(User.count() == 0) {
            Fixtures.loadModels("initial-data.yml");
        }
    }
}
