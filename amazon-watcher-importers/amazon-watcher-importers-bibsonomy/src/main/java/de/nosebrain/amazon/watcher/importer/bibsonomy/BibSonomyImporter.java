package de.nosebrain.amazon.watcher.importer.bibsonomy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bibsonomy.common.enums.GroupingEntity;
import org.bibsonomy.model.Bookmark;
import org.bibsonomy.model.Post;
import org.bibsonomy.model.Tag;
import org.bibsonomy.model.logic.LogicInterface;

import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.WatchMode;
import de.nosebrain.amazon.watcher.services.Importer;


/**
 * 
 * @author nosebrain
 */
public class BibSonomyImporter implements Importer {
	
	private static final int STEP_SIZE = 5;
	private static final String TAG_NAME = "my:amazonwatcher";
	private static final String LIMIT_TAG = "my:price:19,99";
	
	private LogicInterface logic;

	public List<Item> getItems(final Date lastSyncDate) {
		final List<Item> items = new LinkedList<Item>();
		
		int start = 0;
		
		while (true) {
			int found = 0;
			final List<Post<Bookmark>> toWatchBookmarks = this.logic.getPosts(Bookmark.class, GroupingEntity.USER, this.logic.getAuthenticatedUser().getName(), Collections.singletonList(TAG_NAME), null, null, null, start, STEP_SIZE, null);
			
			for (final Post<Bookmark> bookmarkPost : toWatchBookmarks) {
				if (lastSyncDate == null || bookmarkPost.getChangeDate().after(lastSyncDate) || bookmarkPost.getDate().after(lastSyncDate)) {
					found++;
					items.add(this.convert(bookmarkPost));
				}
			}
			start += STEP_SIZE;
			
			if (found != STEP_SIZE) {
				return items;
			}
		}
	}

	private Item convert(Post<Bookmark> bookmarkPost) {
		final Bookmark bookmark = bookmarkPost.getResource();
		
		final Item item = new Item();
		item.setName(bookmark.getTitle());
		try {
			item.setUrl(new URL(bookmark.getUrl()));
		} catch (MalformedURLException e) {
			// TODO: log
			return null;
		}
		
		WatchMode mode = WatchMode.PRICE_CHANGE;
		if (bookmarkPost.getTags().size() > 1) {
			mode = WatchMode.PRICE_LIMIT;
			
			for (final Tag tag : bookmarkPost.getTags()) {
				final String name = tag.getName();
				if (name.startsWith(LIMIT_TAG)) {
					final String limitString = name.substring(LIMIT_TAG.length());
					final float limit = Float.parseFloat(limitString);
					item.setLimit(limit);
				}
			}
		}
		item.setMode(mode);
		
		return item;
	}

	public String getImporterId() {
		return "BibSonomy-Importer";
	}

	/**
	 * @param logic the logic to set
	 */
	public void setLogic(LogicInterface logic) {
		this.logic = logic;
	}

}
