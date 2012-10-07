import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;


public class PageRank {
	
	public static void main(String[] args) throws Exception {
		// phase 1
		Strint phase1input = args[1];
		String phase1output = args[2];
		String[] phase1args = {phase1input, phase1output};
		ToolRunner.run(new Configuration(), new Phase1(), args);
		
		// phase 2
		// iteration
		int iteration = 5;
		for(int count = 0; count < iteration; count++) {
			String phase2input = "phase2_output_iter/part-r-00000";
			String phase2output = "phase2_output_iter/part-r-00000";
			String[] phase2args = {phase2input, phase2output};
			ToolRunner.run(new Configuration(), new Phase2(), phase2args);
		}
	}
}
