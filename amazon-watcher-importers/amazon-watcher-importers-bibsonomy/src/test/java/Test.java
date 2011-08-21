import java.util.List;

import org.bibsonomy.model.logic.LogicInterface;
import org.bibsonomy.rest.client.RestLogicFactory;

import de.nosebrain.amazon.watcher.importer.bibsonomy.BibSonomyImporter;
import de.nosebrain.amazon.watcher.model.Item;


public class Test {
	public static void main(String[] args) {
		final RestLogicFactory factory = new RestLogicFactory();
		final LogicInterface logic = factory.getLogicAccess("nosebrain", "4822942989cefa3f5b7c0159f1090bf1");
		
		final BibSonomyImporter importer = new BibSonomyImporter();
		importer.setLogic(logic);
		
		final List<Item> items = importer.getItems(null);
	}
}
