package cn.invonate.ygoa.Entry;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by liyangyang on 2017/10/22.
 */

public class Mail implements Serializable {
    private String messageID; // 邮件id
    private String subject;//主题
    private String from;//发件人
    private String receiver;//收件人
    private String send_date;//发送时间
    private boolean seen;//是否已读
    private String priority;//邮件优先级:1(High):紧急  3:普通(Normal)  5:低(Low)
    private boolean replay_sign;//是否需要回复
    private int size;//邮件大小
    private boolean isContainerAttachment;//是否有附件
    private ArrayList<FileEntry> files;
    private ArrayList<String> attachments; //附件
    private ArrayList<byte[]> attachmentsInputStreams;
    private ArrayList<Integer> file_size;
    private String content; // 邮件内容
    private boolean html; // 正文是否有html

    public ArrayList<FileEntry> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FileEntry> files) {
        this.files = files;
    }

    public ArrayList<Integer> getFile_size() {
        return file_size;
    }

    public void setFile_size(ArrayList<Integer> file_size) {
        this.file_size = file_size;
    }

    public ArrayList<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<String> attachments) {
        this.attachments = attachments;
    }

    public ArrayList<byte[]> getAttachmentsInputStreams() {
        return attachmentsInputStreams;
    }

    public void setAttachmentsInputStreams(ArrayList<byte[]> attachmentsInputStreams) {
        this.attachmentsInputStreams = attachmentsInputStreams;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isReplay_sign() {
        return replay_sign;
    }

    public void setReplay_sign(boolean replay_sign) {
        this.replay_sign = replay_sign;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isContainerAttachment() {
        return isContainerAttachment;
    }

    public void setContainerAttachment(boolean containerAttachment) {
        isContainerAttachment = containerAttachment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
