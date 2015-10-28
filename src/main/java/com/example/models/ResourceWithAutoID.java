package com.example.models;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jongo.marshall.jackson.oid.Id;

import com.example.utils.ObjectIdJsonSerializer;

public class ResourceWithAutoID implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	@Id
	@JsonSerialize(using = ObjectIdJsonSerializer.class)
	private ObjectId _id;
	private String content;

	public ResourceWithAutoID() {
	}

	public ResourceWithAutoID(String content) {
		this.content = content;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
