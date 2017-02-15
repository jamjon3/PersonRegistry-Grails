package edu.usf.it.PersonRegistry.jobs

class USFIDReplinishTriggerJob {
    static triggers = {
      simple repeatInterval: 5000l // execute job once in 5 seconds
    }

    def execute() {
        // execute job
    }
}
