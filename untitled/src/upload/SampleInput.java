package upload;
import java.util.ArrayList;
import java.util.Set;

/**Created by Hao, 7 March.
 *
 * This class is constructed for testing only!!
 * There are two code file in Java, each has a PSVM method */

public class SampleInput {
	static String address;
	SampleInput(String address){
		this.address=address;
	}

    static ArrayList<String> input = new ArrayList<String>() {
        {
            add(address);
            add("Assignment 1_acq19hh_attemp_2018-11-12-19-17_HttpConnectionUtil.java");
            add("Assignment 1_acq44hh_attemp_2018-11-12-19-17_stock_data_window.java");
            add("Assignment 1_acq19hh_attemp_2018-11-12-19-17_HttpConnectionUtil.java");
        }
    };
}
