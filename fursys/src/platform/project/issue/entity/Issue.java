package platform.project.issue.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

import platform.project.entity.Project;
import platform.project.task.entity.Task;
import wt.content.ContentHolder;
import wt.fc.WTObject;
import wt.org.WTUser;
import wt.ownership.Ownable;
import wt.util.WTException;

@GenAsPersistable(superClass = WTObject.class, interfaces = { Ownable.class },

		properties = {

				@GeneratedProperty(name = "name", type = String.class),

				@GeneratedProperty(name = "description", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),

				@GeneratedProperty(name = "state", type = String.class),

				@GeneratedProperty(name = "issueType", type = String.class)

		},

		foreignKeys = {

				@GeneratedForeignKey(name = "IssueProjectLink",

						foreignKeyRole = @ForeignKeyRole(name = "project", type = Project.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "issue", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "IssueTaskLink",

						foreignKeyRole = @ForeignKeyRole(name = "task", type = Task.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "issue", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "IssueManagerLink",

						foreignKeyRole = @ForeignKeyRole(name = "manager", type = WTUser.class,

								constraints = @PropertyConstraints(required = true)),

						myRole = @MyRole(name = "issue", cardinality = Cardinality.ONE)),

				@GeneratedForeignKey(name = "IssueSolutionLink",

						foreignKeyRole = @ForeignKeyRole(name = "solution", type = Solution.class,

								constraints = @PropertyConstraints(required = false)),

						myRole = @MyRole(name = "issue", cardinality = Cardinality.ONE))

		}

)
public class Issue extends _Issue {
	static final long serialVersionUID = 1;

	public static Issue newIssue() throws WTException {
		Issue instance = new Issue();
		instance.initialize();
		return instance;
	}
}
