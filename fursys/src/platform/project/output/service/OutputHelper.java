package platform.project.output.service;

import java.util.ArrayList;

import platform.project.entity.Project;
import platform.project.output.entity.Output;
import platform.project.output.entity.OutputColumns;
import platform.project.output.entity.ProjectOutputLink;
import platform.project.output.entity.TemplateOutputLink;
import platform.project.task.entity.Task;
import platform.project.template.entity.Template;
import platform.util.CommonUtils;
import platform.util.StringUtils;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;

public class OutputHelper {

	public static final OutputService service = ServiceFactory.getService(OutputService.class);
	public static final OutputHelper manager = new OutputHelper();

	public ArrayList<Output> getTemplateOutput(Template template, Task task) throws Exception {
		ArrayList<Output> list = new ArrayList<>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Output.class, true);

		SearchCondition sc = new SearchCondition(Output.class, "templateReference.key.id", "=",
				template.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });
		query.appendAnd();

		sc = new SearchCondition(Output.class, "taskReference.key.id", "=",
				task.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(Output.class, Output.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Output output = (Output) obj[0];
			list.add(output);
		}
		return list;
	}

	public ArrayList<Output> getOutputs(Template template, Project project, Task task) throws Exception {
		ArrayList<Output> list = new ArrayList<>();

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Output.class, true);

		SearchCondition sc = null;

		if (StringUtils.isNotNull(template)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Output.class, "templateReference.key.id", "=",
					template.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(project)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(Output.class, "projectReference.key.id", "=",
					project.getPersistInfo().getObjectIdentifier().getId());
			query.appendWhere(sc, new int[] { idx });
		}

		if (query.getConditionCount() > 0) {
			query.appendAnd();
		}

		sc = new SearchCondition(Output.class, "taskReference.key.id", "=",
				task.getPersistInfo().getObjectIdentifier().getId());
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(Output.class, Output.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Output output = (Output) obj[0];
			list.add(output);
		}
		return list;
	}

	public ArrayList<OutputColumns> getOutputs(String oid) throws Exception {
		Persistable per = (Persistable) CommonUtils.persistable(oid);
		if (per instanceof Project) {
			return getOutputs((Project) per);
		} else if (per instanceof Template) {
			return getOutputs((Template) per);
		}
		return new ArrayList<>();
	}

	public ArrayList<OutputColumns> getOutputs(Template template) throws Exception {
		ArrayList<OutputColumns> list = new ArrayList<OutputColumns>();

		QueryResult result = PersistenceHelper.manager.navigate(template, "output", TemplateOutputLink.class);
		while (result.hasMoreElements()) {
			Output output = (Output) result.nextElement();
			list.add(new OutputColumns(output));
		}
		return list;
	}

	public ArrayList<OutputColumns> getOutputs(Project project) throws Exception {
		ArrayList<OutputColumns> list = new ArrayList<OutputColumns>();

		QueryResult result = PersistenceHelper.manager.navigate(project, "output", ProjectOutputLink.class);
		while (result.hasMoreElements()) {
			Output output = (Output) result.nextElement();
			list.add(new OutputColumns(output));
		}
		return list;
	}
}
