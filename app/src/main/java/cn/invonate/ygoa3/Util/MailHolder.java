package cn.invonate.ygoa3.Util;


import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;
import javax.mail.internet.MimeMessage;

import cn.invonate.ygoa3.Entry.Mail;
import cn.invonate.ygoa3.Entry.MailNew;

/**
 * Created by liyangyang on 2017/10/26.
 */

public class MailHolder {
    public static List<MimeMessage> mails;
    public static List<Mail> mail_model;

    public static Folder folder;

    public static ArrayList<MailNew.ResultBean.MailsBean> mailsBeans;

}
