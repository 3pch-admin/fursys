package platform.wood.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import platform.tree.entity.Combination;
import platform.wood.entity.Material;
import platform.wood.entity.MaterialDTO;
import platform.wood.entity.Process;
import platform.wood.entity.ProcessDTO;
import platform.wood.entity.Treatment;
import platform.wood.entity.TreatmentDTO;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.services.ServiceFactory;

public class WoodHelper {

	public static final WoodService service = ServiceFactory.getService(WoodService.class);
	public static final WoodHelper manager = new WoodHelper();

	public List<MaterialDTO> material(Map<String, Object> params) throws Exception {
		// 검색 조건
		List<MaterialDTO> list = new ArrayList<MaterialDTO>();
		try {
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Material.class, true);
			QueryResult result = PersistenceHelper.manager.find(query);

			ClassAttribute ca = new ClassAttribute(Material.class, Material.CODE);
			OrderBy by = new OrderBy(ca, true);
			query.appendOrderBy(by, new int[] { idx });

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				Material material = (Material) obj[0];
				list.add(new MaterialDTO(material));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public List<TreatmentDTO> treatment(Map<String, Object> params) throws Exception {
		// 검색 조건
		List<TreatmentDTO> list = new ArrayList<TreatmentDTO>();
		try {
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Treatment.class, true);
			QueryResult result = PersistenceHelper.manager.find(query);

			ClassAttribute ca = new ClassAttribute(Treatment.class, Treatment.CODE);
			OrderBy by = new OrderBy(ca, true);
			query.appendOrderBy(by, new int[] { idx });

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				Treatment treatment = (Treatment) obj[0];
				list.add(new TreatmentDTO(treatment));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public List<ProcessDTO> process(Map<String, Object> params) throws Exception {
		// 검색 조건
		List<ProcessDTO> list = new ArrayList<ProcessDTO>();
		try {
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Process.class, true);
			QueryResult result = PersistenceHelper.manager.find(query);

			ClassAttribute ca = new ClassAttribute(Process.class, Process.CODE);
			OrderBy by = new OrderBy(ca, true);
			query.appendOrderBy(by, new int[] { idx });

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				Process process = (Process) obj[0];
				list.add(new ProcessDTO(process));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

}
