# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('whatodo', '0002_auto_20150501_2152'),
    ]

    operations = [
        migrations.AddField(
            model_name='whatodouser',
            name='imageUser',
            field=models.ImageField(upload_to='/var/www/dfournier.ovh/whatodo/media', default='/static/images/jeanDujardin.jpg'),
            preserve_default=True,
        ),
    ]
