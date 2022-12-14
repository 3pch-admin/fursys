package platform.color.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import platform.color.entity.Color;
import platform.color.entity.ColorLink;
import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOMLink;
import platform.ebom.service.EBOMHelper;
import platform.ebom.vo.BOMTreeNode;
import platform.util.CommonUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardColorService extends StandardManager implements ColorService {

	public static StandardColorService newStandardColorService() throws WTException {
		StandardColorService instance = new StandardColorService();
		instance.initialize();
		return instance;
	}

	@Override
	public void create(Map<String, Object> params) throws Exception {
		String json = (String) params.get("json");
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			String jsonStr = new String(DatatypeConverter.parseBase64Binary(json), "UTF-8");
			Type listType = new TypeToken<ArrayList<BOMTreeNode>>() {
			}.getType();

			Gson gson = new Gson();
			List<BOMTreeNode> nodes = gson.fromJson(jsonStr, listType);

			for (BOMTreeNode node : nodes) {
				WTPart part = (WTPart) CommonUtils.persistable(node.getOid()); // EBOM객체랑 연결될 파트들..
				String applyColor = node.getApplyColor();

				if (!StringUtils.isNotNull(node.getParent())) {

					if (ColorHelper.manager.isColorMaster(part, applyColor)) {
						throw new Exception("이미 등록된 컬러셋이 있습니다.");
					}

					Color color = Color.newColor();
					color.setPart(part);
					color.setColorType("MASTER");
					color.setColor(applyColor);
					color.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.save(color);
					// 부모가 없는건 링크가 없다..
				} else {
					WTPart parentPart = (WTPart) CommonUtils.persistable(node.getParent());
					Color parent = ColorHelper.manager.getColor(parentPart);
					Color color = Color.newColor();
					color.setPart(part);
					color.setColor(applyColor);
					color.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.save(color);

					ColorLink link = ColorLink.newColorLink(parent, color);
					link.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.save(link);
				}

			}
			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public Color delete(String oid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
