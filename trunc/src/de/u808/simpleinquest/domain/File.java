package de.u808.simpleinquest.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class File {

	private static final long serialVersionUID = -6494364644766052129L;
	protected long id;
	private String name;
	private File parent;
	private List<File> children;
	private long thread;
	private long left = 1;
	private long right = 2;

	public File() {
		this.children = new LinkedList<File>();
	}

	public File(String name) {
		this();
		this.name = name;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	@Column (nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(cascade={CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="FILE_ID", nullable=true) 
	public File getParent() {
		return parent;
	}

	public void setParent(File parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
	public List<File> getChildren() {
		return children;
	}

	public void setChildren(List<File> children) {
		this.children = children;
	}

	@Basic
	@Column (nullable=false)
	public long getThread() {
		return thread;
	}

	public void setThread(long thread) {
		this.thread = thread;
	}

	@Basic
	@Column (nullable=false)
	public long getLeft() {
		return left;
	}

	public void setLeft(long left) {
		this.left = left;
	}

	@Basic
	@Column (nullable=false)
	public long getRight() {
		return right;
	}

	public void setRight(long right) {
		this.right = right;
	}

	public void addChild(File child) {
		if (child == null) {
			throw new IllegalArgumentException(
					"Can't add a null node as child.");
		}

		// Remove from old parent - one-to-many multiplicity
		if (child.getParent() != null) {
			child.getParent().getChildren().remove(child);
		}

		// Set parent in child
		child.setParent(this);

		// Set child in parent
		this.getChildren().add(child);
	}

	public void removeChild(File child) {
		if (child == null) {
			return;
		}
		// Remove from parent and set parent to null
		if (child.getParent() != null) {
			child.getParent().getChildren().remove(child);
		}
		child.setParent(null);
	}

}
