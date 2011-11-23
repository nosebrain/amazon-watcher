//package de.nosebrain.amazon.watcher.importer.bibsonomy;
//
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URL;
//import java.util.Collections;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import org.bibsonomy.model.Bookmark;
//import org.bibsonomy.model.Post;
//import org.bibsonomy.model.Resource;
//import org.bibsonomy.model.Tag;
//import org.bibsonomy.model.logic.LogicInterface;
//import org.bibsonomy.model.sync.ConflictResolutionStrategy;
//import org.bibsonomy.model.sync.SynchronizationAction;
//import org.bibsonomy.model.sync.SynchronizationData;
//import org.bibsonomy.model.sync.SynchronizationDirection;
//import org.bibsonomy.model.sync.SynchronizationPost;
//import org.bibsonomy.model.sync.SynchronizationStatus;
//import org.bibsonomy.util.StringUtils;
//
//import de.nosebrain.amazon.watcher.AmazonWatcherService;
//import de.nosebrain.amazon.watcher.model.Item;
//import de.nosebrain.amazon.watcher.model.WatchMode;
//import de.nosebrain.amazon.watcher.services.SyncSerice;
//
//
///**
// *
// * @author nosebrain
// */
//public class BibSonomyImporter implements SyncSerice {
//	private static final Class<? extends Resource> RESOURCE_TYPE = Bookmark.class;
//
//	private static final Tag SYSTEM_TAG = new Tag("my:amazonwatcher");
//	private static final String LIMIT_TAG = "my:price:";
//
//
//	private LogicInterface logic;
//	private URI ownURI;
//
//	@Override
//	public void sync(final AmazonWatcherService service) {
//		final Map<String, Item> items = convertToMap(service.getItems());
//		final String userName = this.logic.getAuthenticatedUser().getName();
//
//		final List<SynchronizationPost> syncPlan = this.logic.getSyncPlan(userName, this.ownURI, RESOURCE_TYPE, convert(items), ConflictResolutionStrategy.SERVER_WINS, SynchronizationDirection.BOTH);
//
//		final SynchronizationData lastSyncData = this.logic.getLastSyncData(userName, this.ownURI, RESOURCE_TYPE);
//		if (lastSyncData == null) {
//			throw new IllegalStateException();
//		}
//
//		this.logic.updateSyncData(userName, this.ownURI, RESOURCE_TYPE, null, SynchronizationStatus.RUNNING, "");
//
//		final List<Item> itemsToCreate = new LinkedList<Item>();
//		final List<Item> itemsToDelete = new LinkedList<Item>();
//		final List<Post<? extends Resource>> postsToCreate = new LinkedList<Post<? extends Resource>>();
//
//		for (SynchronizationPost synchronizationPost : syncPlan) {
//			final SynchronizationAction action = synchronizationPost.getAction();
//			final String hash = synchronizationPost.getIntraHash();
//			final Item item = items.get(hash);
//			switch (action) {
//			case CREATE_CLIENT:
//				@SuppressWarnings("unchecked")
//				final Post<Bookmark> post = (Post<Bookmark>) synchronizationPost.getPost();
//
//				/*
//				 * only create if SYSTEM_TAG is set
//				 */
//				if (post.getTags().contains(SYSTEM_TAG)) {
//					final Item toCreate = convert(post);
//					itemsToCreate.add(toCreate);
//				}
//
//				break;
//			case UPDATE_CLIENT:
//
//				break;
//			case UPDATE_SERVER:
//
//				break;
//			case CREATE_SERVER:
//
//
//				break;
//			case DELETE_CLIENT:
//				/*
//				 * could also contain posts without SYSTEM_TAG
//				 */
//				if (item != null) {
//					itemsToDelete.add(item);
//				}
//				break;
//			case DELETE_SERVER:
//
//				this.logic.deletePosts(userName, Collections.singletonList(hash));
//			default:
//				break;
//			}
//		}
//	}
//
//	public void createItemsOnRemoteService(final Set<Item> items) {
//
//	}
//
//	private List<SynchronizationPost> convert(Map<String, Item> items) {
//		final List<SynchronizationPost> posts = new LinkedList<SynchronizationPost>();
//
//		for (Entry<String, Item> entry : items.entrySet()) {
//			final SynchronizationPost synchronizationPost = new SynchronizationPost();
//			synchronizationPost.setIntraHash(entry.getKey());
//			final Item item = entry.getValue();
//			synchronizationPost.setCreateDate(item.getDate());
//			synchronizationPost.setChangeDate(item.getChangeDate());
//			posts.add(synchronizationPost);
//		}
//
//		return posts;
//	}
//
//	private Map<String, Item> convertToMap(List<Item> items) {
//		final Map<String, Item> itemsMap = new LinkedHashMap<String, Item>();
//
//		for (final Item item : items) {
//			final String hash = StringUtils.getMD5Hash(item.getUrl().toString());
//			itemsMap.put(hash, item);
//		}
//
//		return itemsMap;
//	}
//
//	private Item convert(Post<Bookmark> bookmarkPost) {
//		final Bookmark bookmark = bookmarkPost.getResource();
//
//		final Item item = new Item();
//		item.setName(bookmark.getTitle());
//		try {
//			item.setUrl(new URL(bookmark.getUrl()));
//		} catch (MalformedURLException e) {
//			// TODO: log
//			return null;
//		}
//
//		WatchMode mode = WatchMode.PRICE_CHANGE;
//		if (bookmarkPost.getTags().size() > 1) {
//			mode = WatchMode.PRICE_LIMIT;
//
//			for (final Tag tag : bookmarkPost.getTags()) {
//				final String name = tag.getName();
//				if (name.startsWith(LIMIT_TAG)) {
//					final String limitString = name.substring(LIMIT_TAG.length());
//					final float limit = Float.parseFloat(limitString);
//					item.setLimit(limit);
//				}
//			}
//		}
//		item.setMode(mode);
//
//		return item;
//	}
//
//	/**
//	 * @param ownURI the ownURI to set
//	 */
//	public void setOwnURI(URI ownURI) {
//		this.ownURI = ownURI;
//	}
//
//	/**
//	 * @param logic the logic to set
//	 */
//	public void setLogic(LogicInterface logic) {
//		this.logic = logic;
//	}
//
//}
