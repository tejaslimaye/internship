DELIMITER ;;
CREATE TRIGGER TRG_INSERT_TEST_RUN
AFTER INSERT 
ON TEST_RUN
FOR EACH ROW
BEGIN

INSERT INTO TEST_EXECUTION (TESTRUN_ID,TESTCASE_ID) SELECT tr.testrun_id,tcjm.testcase_id from test_run tr, test_case_job_mapping tcjm where tr.test_job_id = tcjm.testjob_id and tr.testrun_id  = NEW.testrun_id;

END ;;
DELIMITER ;
