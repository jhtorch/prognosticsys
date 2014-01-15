package com.nian.firstproject.client.widges;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.nian.firstproject.client.blobs.BlobDataFilter;
import com.nian.firstproject.client.rpc.RpcInit;
import com.nian.firstproject.client.rpc.RpcServiceAsync;

//The delete widget in "Group patients" tab

public class DeleteWidget extends Composite implements ClickHandler {

	private RpcServiceAsync rpc;
	private PushButton bDelete = new PushButton("Delete");
	private String blobKey;

	public DeleteWidget(String blobKey) {
		this.blobKey = blobKey;

		initWidget(bDelete);

		bDelete.addClickHandler(this);

		rpc = RpcInit.init();
	}

	private void fireChange() {
		NativeEvent nativeEvent = Document.get().createChangeEvent();
		ChangeEvent.fireNativeEvent(nativeEvent, this);
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {

		return addDomHandler(handler, ChangeEvent.getType());
	}

	private void delete() {

		BlobDataFilter filter = new BlobDataFilter();
		filter.setBlobKey(blobKey);

		rpc.deleteBlob(filter, new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean b) {
				if (b != null && b == true) {
					fireChange();
				} else {
					Window.alert("Oops wasn't able to delete that blob.");
				}
			}

			public void onFailure(Throwable caught) {
				Window.alert("Oops couldn't delete that blob.");
			}
		});
	}

	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();

		if (sender == bDelete) {
			delete();
		}

	}

}
