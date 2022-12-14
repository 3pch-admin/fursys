package platform.util;

import com.ptc.wvs.server.util.FileHelper;
import com.ptc.wvs.server.util.PublishUtils;

import wt.content.ContentRoleType;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.ReferenceFactory;
import wt.part.WTPart;
import wt.representation.Representation;

public class ThumbnailUtils {

	private ThumbnailUtils() {

	}

	public static String[] thumbnails(String oid) throws Exception {
		ReferenceFactory rf = new ReferenceFactory();
		Persistable persistable = (Persistable) rf.getReference(oid).getObject();
		return thumbnails(persistable);
	}

	public static String[] thumbnails(Persistable persistable) throws Exception {

		String[] thumbnails = new String[4];
		if (persistable != null) {
			String thumbnail = FileHelper.getViewContentURLForType(PublishUtils.findRepresentable(persistable),
					ContentRoleType.THUMBNAIL);
			String thumbnail_mini = FileHelper.getViewContentURLForType(PublishUtils.findRepresentable(persistable),
					ContentRoleType.THUMBNAIL_SMALL);

			if (thumbnail_mini == null) {
				thumbnail_mini = "/Windchill/jsp/images/no-view.png";
			}

			if (thumbnail == null) {
				thumbnail = "/Windchill/jsp/images/productview_openin_250.png";
			}

			Representation representation = PublishUtils.getRepresentation(persistable, true, null, false);
			String copyTag = "";
			if (representation != null) {
				copyTag = PublishUtils.getRefFromObject(representation);
			}

			thumbnails[0] = thumbnail;
			thumbnails[1] = thumbnail_mini;
			thumbnails[2] = copyTag;
			thumbnails[3] = persistable.getPersistInfo().getObjectIdentifier().getStringValue();
		}
		if (persistable == null) {
			thumbnails[0] = "/Windchill/jsp/images/productview_openin_250.png";
			thumbnails[1] = "/Windchill/jsp/images/no-view.png";
			thumbnails[2] = "";
			thumbnails[3] = "";
		}
		return thumbnails;
	}

	public static String magnify(Persistable per) throws Exception {
		if (!(per instanceof WTPart) && !(per instanceof EPMDocument)) {
			return "";
		}
		String name = ((RevisionControlled) per).getName();
		// magnify js 필요..
		String href = ThumbnailUtils.thumbnails(per)[0];
		String src = ThumbnailUtils.thumbnails(per)[1];
		String magnify = "<img data-magnify='gallery' data-caption='" + name + "' src='" + src + "' href='" + href
				+ "'>";
		return magnify;
	}
}